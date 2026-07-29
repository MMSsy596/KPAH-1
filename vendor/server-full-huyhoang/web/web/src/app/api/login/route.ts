import { timingSafeEqual } from 'node:crypto';

import { NextRequest, NextResponse } from 'next/server';
import type { RowDataPacket } from 'mysql2/promise';

import { attachSessionCookie, createAuthSession } from '@/lib/auth';
import { getAccountPool } from '@/lib/db';
import { mysqlPasswordHash } from '@/lib/account-register';

export const runtime = 'nodejs';

type LoginPayload = {
  username?: unknown;
  password?: unknown;
};

function cleanText(value: unknown): string {
  return typeof value === 'string' ? value.trim() : '';
}

function safeEqual(left: string, right: string): boolean {
  const leftBuffer = Buffer.from(left, 'utf8');
  const rightBuffer = Buffer.from(right, 'utf8');

  if (leftBuffer.length !== rightBuffer.length) {
    return false;
  }

  return timingSafeEqual(leftBuffer, rightBuffer);
}

export async function POST(request: NextRequest) {
  const payload = (await request.json().catch(() => null)) as LoginPayload | null;

  if (!payload) {
    return NextResponse.json({ ok: false, message: 'Không đọc được dữ liệu đăng nhập.' }, { status: 400 });
  }

  const username = cleanText(payload.username).toLowerCase();
  const password = cleanText(payload.password);

  if (!/^[a-z0-9_]{4,20}$/.test(username)) {
    return NextResponse.json({ ok: false, message: 'Tài khoản không hợp lệ.' }, { status: 400 });
  }

  if (!password) {
    return NextResponse.json({ ok: false, message: 'Vui lòng nhập mật khẩu.' }, { status: 400 });
  }

  try {
    const pool = getAccountPool();
    const [rows] = await pool.query<RowDataPacket[]>(
      `
        SELECT id, username, password, ban
        FROM team_user
        WHERE LOWER(username) = LOWER(?)
        LIMIT 1
      `,
      [username]
    );

    const account = rows[0];
    const passwordHash = mysqlPasswordHash(password);

    if (!account || !safeEqual(String(account.password ?? ''), passwordHash)) {
      return NextResponse.json(
        { ok: false, message: 'Tài khoản hoặc mật khẩu không chính xác.' },
        { status: 401 }
      );
    }

    const ban = Number(account.ban ?? 0);

    if (ban === 1) {
      return NextResponse.json(
        {
          ok: false,
          message: 'Tài khoản của bạn chưa được kích hoạt hoặc đang tạm khóa. Vui lòng liên hệ admin.'
        },
        { status: 403 }
      );
    }

    if (ban === 3) {
      return NextResponse.json(
        {
          ok: false,
          message: 'Tài khoản hiện không thể đăng nhập. Vui lòng liên hệ admin để được hỗ trợ.'
        },
        { status: 403 }
      );
    }

    const response = NextResponse.json({
      ok: true,
      message: 'Đăng nhập thành công.'
    });

    attachSessionCookie(response, createAuthSession(Number(account.id), String(account.username)));

    return response;
  } catch (error) {
    console.error('[login] failed', error);

    return NextResponse.json(
      { ok: false, message: 'Hệ thống đăng nhập đang tạm bận. Vui lòng thử lại sau.' },
      { status: 500 }
    );
  }
}
