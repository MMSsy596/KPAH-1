import type { Metadata } from 'next';
import Link from 'next/link';
import { notFound } from 'next/navigation';

import { getHomePostCategoryLabel, getPublishedHomePostById } from '@/lib/home-posts';

type PostPageProps = {
  params: Promise<{ id: string }>;
};

export async function generateMetadata({ params }: PostPageProps): Promise<Metadata> {
  const { id } = await params;
  const post = await getPublishedHomePostById(Number(id));

  if (!post) {
    return {
      title: 'Bài viết không tồn tại - KPAH'
    };
  }

  return {
    title: `${post.title} - KPAH`,
    description: post.content.slice(0, 160)
  };
}

export default async function PostDetailPage({ params }: PostPageProps) {
  const { id } = await params;
  const post = await getPublishedHomePostById(Number(id));

  if (!post) {
    notFound();
  }

  return (
    <div className="register-page">
      <div className="register-page__backdrop" />
      <div className="register-page__shell">
        <div className="register-page__topbar">
          <Link className="register-page__brand" href="/">
            <img src="/logo/logo.png" alt="KPAH" />
            <span>KPAH</span>
          </Link>
          <Link className="register-page__backlink" href="/">
            Về trang chủ
          </Link>
        </div>

        <div className="register-page__card">
          <div className="register-page__intro">
            <p className="register-page__eyebrow">{getHomePostCategoryLabel(post.category)}</p>
            <h1>{post.title}</h1>
            <p className="register-page__lead">
              {post.publishedAt ? `Đăng lúc ${post.publishedAt}` : 'Bài viết đang hiển thị trên trang chủ KPAH.'}
            </p>
          </div>

          <div className="admin-panel post-detail-card">
            <div className="post-detail-card__content">
              {post.content.split(/\r?\n/).map((line, index) => (
                <p key={`${post.id}-${index}`}>{line || '\u00A0'}</p>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
