import 'server-only';

import { createHmac, timingSafeEqual } from 'node:crypto';

import { cookies } from 'next/headers';
import { NextResponse } from 'next/server';

import { readGameServerSettings } from '@/lib/db';

export const SESSION_COOKIE_NAME = 'kpah_session';

const SESSION_TTL_MS = 1000 * 60 * 60 * 24 * 7;

export type AuthSession = {
  userId: number;
  username: string;
  issuedAt: number;
  expiresAt: number;
};

function toBase64Url(value: string): string {
  return Buffer.from(value, 'utf8').toString('base64url');
}

function fromBase64Url(value: string): string {
  return Buffer.from(value, 'base64url').toString('utf8');
}

function getSessionSecret(): string {
  const settings = readGameServerSettings();

  return (
    process.env.KPAH_WEB_SESSION_SECRET
    || settings.get('web.sessionSecret')
    || settings.get('sv.clientAuthSecret')
    || settings.get('sv.localAdminToken')
    || 'kpah-web-session-secret'
  );
}

function useSecureCookies(): boolean {
  const configured = process.env.KPAH_COOKIE_SECURE;
  return configured == null ? process.env.NODE_ENV !== 'development' : configured === '1';
}

function signPayload(encodedPayload: string): string {
  return createHmac('sha256', getSessionSecret()).update(encodedPayload).digest('base64url');
}

function safeEqual(left: string, right: string): boolean {
  const leftBuffer = Buffer.from(left, 'utf8');
  const rightBuffer = Buffer.from(right, 'utf8');

  if (leftBuffer.length !== rightBuffer.length) {
    return false;
  }

  return timingSafeEqual(leftBuffer, rightBuffer);
}

export function createAuthSession(userId: number, username: string): AuthSession {
  const issuedAt = Date.now();

  return {
    userId,
    username,
    issuedAt,
    expiresAt: issuedAt + SESSION_TTL_MS
  };
}

export function encodeAuthSession(session: AuthSession): string {
  const payload = toBase64Url(JSON.stringify(session));
  const signature = signPayload(payload);

  return `${payload}.${signature}`;
}

export function decodeAuthSession(token: string | undefined | null): AuthSession | null {
  if (!token) {
    return null;
  }

  const [payload, signature] = token.split('.');
  if (!payload || !signature) {
    return null;
  }

  const expectedSignature = signPayload(payload);
  if (!safeEqual(signature, expectedSignature)) {
    return null;
  }

  try {
    const parsed = JSON.parse(fromBase64Url(payload)) as Partial<AuthSession>;
    if (
      typeof parsed.userId !== 'number'
      || typeof parsed.username !== 'string'
      || typeof parsed.issuedAt !== 'number'
      || typeof parsed.expiresAt !== 'number'
    ) {
      return null;
    }

    if (parsed.expiresAt <= Date.now()) {
      return null;
    }

    return {
      userId: parsed.userId,
      username: parsed.username,
      issuedAt: parsed.issuedAt,
      expiresAt: parsed.expiresAt
    };
  } catch {
    return null;
  }
}

export async function getCurrentSession(): Promise<AuthSession | null> {
  const cookieStore = await cookies();
  return decodeAuthSession(cookieStore.get(SESSION_COOKIE_NAME)?.value);
}

export function attachSessionCookie(response: NextResponse, session: AuthSession): void {
  response.cookies.set({
    name: SESSION_COOKIE_NAME,
    value: encodeAuthSession(session),
    httpOnly: true,
    sameSite: 'lax',
    secure: useSecureCookies(),
    path: '/',
    expires: new Date(session.expiresAt)
  });
}

export function clearSessionCookie(response: NextResponse): void {
  response.cookies.set({
    name: SESSION_COOKIE_NAME,
    value: '',
    httpOnly: true,
    sameSite: 'lax',
    secure: useSecureCookies(),
    path: '/',
    expires: new Date(0)
  });
}
