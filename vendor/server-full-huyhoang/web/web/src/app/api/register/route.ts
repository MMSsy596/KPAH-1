import { NextRequest, NextResponse } from 'next/server';
import type { RowDataPacket } from 'mysql2/promise';

import {
  createSubmissionFingerprint,
  ensurePendingRegistrationSchema,
  getAccountPool,
  mysqlPasswordHash
} from '@/lib/account-register';

export const runtime = 'nodejs';

type RegisterPayload = {
  username?: unknown;
  email?: unknown;
  phone?: unknown;
  password?: unknown;
  confirmPassword?: unknown;
  startedAt?: unknown;
  website?: unknown;
};

const RESERVED_USERNAMES = new Set([
  'admin',
  'administrator',
  'gm',
  'mod',
  'support',
  'system',
  'owner',
  'root',
  'test',
  'guest'
]);

const DISPOSABLE_EMAIL_DOMAINS = new Set([
  '10minutemail.com',
  '10minutemail.net',
  'guerrillamail.com',
  'mailinator.com',
  'tempmail.com',
  'temp-mail.org',
  'throwawaymail.com',
  'yopmail.com'
]);

function cleanText(value: unknown): string {
  return typeof value === 'string' ? value.trim() : '';
}

function normalizeUsername(value: string): string {
  return value.trim().toLowerCase();
}

function normalizeEmail(value: string): string {
  return value.trim().toLowerCase();
}

function normalizePhone(value: string): string {
  const normalized = value.replace(/[^\d+]/g, '');
  if (normalized.startsWith('+')) {
    return `+${normalized.slice(1).replace(/\+/g, '')}`;
  }
  return normalized.replace(/\+/g, '');
}

function getClientIp(request: NextRequest): string {
  const forwarded = request.headers.get('cf-connecting-ip')
    ?? request.headers.get('x-real-ip')
    ?? request.headers.get('x-forwarded-for')
    ?? 'unknown';
  return forwarded.split(',')[0]?.trim().slice(0, 64) || 'unknown';
}

function validatePayload(payload: RegisterPayload): string | null {
  const username = normalizeUsername(cleanText(payload.username));
  const email = normalizeEmail(cleanText(payload.email));
  const phone = normalizePhone(cleanText(payload.phone));
  const password = cleanText(payload.password);
  const confirmPassword = cleanText(payload.confirmPassword);
  const startedAt = Number(payload.startedAt);

  if (Number.isFinite(startedAt) && Date.now() - startedAt > 1000 * 60 * 60 * 6) {
    return 'Phiên đăng ký đã hết hạn. Vui lòng tải lại trang.';
  }

  if (!/^[a-z0-9_]{4,20}$/.test(username)) {
    return 'Tên tài khoản chỉ gồm 4-20 ký tự chữ thường, số hoặc dấu gạch dưới.';
  }
  if (RESERVED_USERNAMES.has(username)) {
    return 'Tên tài khoản này không được phép sử dụng.';
  }
  if (/^\d+$/.test(username) || /(test|spam|clone|fake)/.test(username)) {
    return 'Tên tài khoản không hợp lệ. Vui lòng chọn tên khác.';
  }

  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email) || email.length > 100) {
    return 'Email không hợp lệ.';
  }
  const emailDomain = email.split('@')[1] ?? '';
  if (DISPOSABLE_EMAIL_DOMAINS.has(emailDomain)) {
    return 'Hệ thống không chấp nhận email tạm thời.';
  }

  if (phone && !/^\+?\d{9,15}$/.test(phone)) {
    return 'Số điện thoại không hợp lệ.';
  }

  if (password.length < 8 || password.length > 32) {
    return 'Mật khẩu phải dài từ 8 đến 32 ký tự.';
  }
  if (!/[a-z]/i.test(password) || !/\d/.test(password)) {
    return 'Mật khẩu phải có ít nhất 1 chữ cái và 1 chữ số.';
  }
  if (password !== confirmPassword) {
    return 'Mật khẩu nhập lại không khớp.';
  }

  return null;
}

export async function POST(request: NextRequest) {
  const payload = (await request.json().catch(() => null)) as RegisterPayload | null;
  if (!payload) {
    return NextResponse.json({ ok: false, message: 'Không đọc được dữ liệu đăng ký.' }, { status: 400 });
  }

  if (cleanText(payload.website)) {
    return NextResponse.json({
      ok: true,
      message: 'Yêu cầu đăng ký đã được ghi nhận. Vui lòng chờ xét duyệt.'
    });
  }

  const validationError = validatePayload(payload);
  if (validationError) {
    return NextResponse.json({ ok: false, message: validationError }, { status: 400 });
  }

  const username = normalizeUsername(cleanText(payload.username));
  const email = normalizeEmail(cleanText(payload.email));
  const phone = normalizePhone(cleanText(payload.phone));
  const password = cleanText(payload.password);
  const clientIp = getClientIp(request);
  const userAgent = (request.headers.get('user-agent') ?? 'unknown').slice(0, 255);
  const fingerprint = createSubmissionFingerprint([clientIp, username, email, userAgent.slice(0, 120)]);
  const pool = getAccountPool();

  try {
    await ensurePendingRegistrationSchema();

    const [existingAccountRows] = await pool.query<RowDataPacket[]>(
      `
        SELECT id
        FROM team_user
        WHERE LOWER(username) = LOWER(?) OR LOWER(email) = LOWER(?)
        LIMIT 1
      `,
      [username, email]
    );
    if (existingAccountRows.length > 0) {
      return NextResponse.json({ ok: false, message: 'Tai khoan hoac email da ton tai.' }, { status: 409 });
      
    }

    const [pendingRows] = await pool.query<RowDataPacket[]>(
      `
        SELECT id, status, requested_at
        FROM pending_account_registrations
        WHERE LOWER(username) = LOWER(?) OR LOWER(email) = LOWER(?)
        ORDER BY requested_at DESC
        LIMIT 1
      `,
      [username, email]
    );
    if (pendingRows.length > 0 && pendingRows[0].status === 'pending') {
      return NextResponse.json(
        { ok: false, message: 'Tai khoan hoac email nay dang co yeu cau cho duyet.' },
        
        { status: 409 }
      );
    }

    const [recentIpRows] = await pool.query<RowDataPacket[]>(
      `
        SELECT COUNT(*) AS total
        FROM pending_account_registrations
        WHERE request_ip = ?
          AND requested_at >= DATE_SUB(NOW(), INTERVAL 1 HOUR)
      `,
      [clientIp]
    );
    if (Number(recentIpRows[0]?.total ?? 0) >= 3) {
      return NextResponse.json(
        { ok: false, message: 'IP nay da gui qua nhieu yeu cau. Vui long thu lai sau 1 gio.' },
        
        { status: 429 }
      );
    }

    const [recentFingerprintRows] = await pool.query<RowDataPacket[]>(
      `
        SELECT COUNT(*) AS total
        FROM pending_account_registrations
        WHERE submission_fingerprint = ?
          AND requested_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
      `,
      [fingerprint]
    );
    if (Number(recentFingerprintRows[0]?.total ?? 0) > 0) {
      return NextResponse.json(
        { ok: false, message: 'Yeu cau dang ky trung lap. Vui long cho admin xu ly truoc.' },
        
        { status: 429 }
      );
    }

    const passwordHash = mysqlPasswordHash(password);
    await pool.query(
      `
        INSERT INTO pending_account_registrations (
          username,
          password_hash,
          email,
          phone,
          request_ip,
          user_agent,
          submission_fingerprint
        )
        VALUES (?, ?, ?, ?, ?, ?, ?)
      `,
      [username, passwordHash, email, phone, clientIp, userAgent, fingerprint]
    );

    return NextResponse.json(
      {
        ok: true,
        message: 'Gửi yêu cầu thành công. Tài khoản của bạn đang chờ xét duyệt.'
      },
      { status: 201 }
    );
  } catch (error) {
    console.error('[register] failed', error);
    return NextResponse.json(
      { ok: false, message: 'Hệ thống đăng ký đang tạm bận. Vui lòng thử lại sau.' },
      { status: 500 }
    );
  }
}
