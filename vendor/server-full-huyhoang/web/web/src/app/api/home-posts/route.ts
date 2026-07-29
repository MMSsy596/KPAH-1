import { NextResponse } from 'next/server';

import { listPublishedHomePostsByCategory } from '@/lib/home-posts';

export const runtime = 'nodejs';

export async function GET() {
  const grouped = await listPublishedHomePostsByCategory();
  return NextResponse.json(grouped, {
    headers: {
      'Cache-Control': 'no-store'
    }
  });
}
