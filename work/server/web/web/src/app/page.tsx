'use client';

import React, { useEffect, useRef, useState } from 'react';

const downloadLinks = {
  ios: 'https://testflight.apple.com/join/mBAvwWH1',
  jar: '/tai-game',
  apk: 'https://www.mediafire.com/file/qc99oyw3968d9n4/KPAH_275_grinding-signed.apk/file',
  pc: '/tai-game'
};

type TabKey = 'new' | 'feature' | 'guide';
type HomePost = {
  id: number;
  category: TabKey;
  title: string;
  content: string;
};
type HomePostMap = Record<TabKey, HomePost[]>;

const EMPTY_HOME_POSTS: HomePostMap = {
  new: [],
  feature: [],
  guide: []
};

const DESKTOP_DESIGN_WIDTH = 2000;
const DESKTOP_DESIGN_HEIGHT = 2692;
const MOBILE_DESIGN_WIDTH = 960;
const MOBILE_DESIGN_HEIGHT = 3380;

export default function HomePage() {
  const [activeTab, setActiveTab] = useState<TabKey>('new');
  const [wrapperHeight, setWrapperHeight] = useState(DESKTOP_DESIGN_HEIGHT);
  const [homePosts, setHomePosts] = useState<HomePostMap>(EMPTY_HOME_POSTS);
  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const updateScale = () => {
      if (!wrapperRef.current) return;

      const vw = window.innerWidth;
      const isMobile = vw <= MOBILE_DESIGN_WIDTH;
      const baseWidth = isMobile ? MOBILE_DESIGN_WIDTH : DESKTOP_DESIGN_WIDTH;
      const baseHeight = isMobile ? MOBILE_DESIGN_HEIGHT : DESKTOP_DESIGN_HEIGHT;
      const scale = Math.min(vw / baseWidth, 1);

      wrapperRef.current.style.transform = `scale(${scale})`;
      setWrapperHeight(baseHeight * scale);
    };

    updateScale();
    window.addEventListener('resize', updateScale);
    return () => window.removeEventListener('resize', updateScale);
  }, []);

  useEffect(() => {
    let cancelled = false;

    fetch('/api/home-posts', { cache: 'no-store' })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error('Khong the tai bai viet');
        }

        return response.json() as Promise<HomePostMap>;
      })
      .then((data) => {
        if (!cancelled) {
          setHomePosts({
            new: Array.isArray(data.new) ? data.new : [],
            feature: Array.isArray(data.feature) ? data.feature : [],
            guide: Array.isArray(data.guide) ? data.guide : []
          });
        }
      })
      .catch(() => {
        if (!cancelled) {
          setHomePosts(EMPTY_HOME_POSTS);
        }
      });

    return () => {
      cancelled = true;
    };
  }, []);

  const getActivePosts = () => homePosts[activeTab] ?? [];

  return (
    <div className="homepage-body" style={{ background: '#10121a', minHeight: '100vh' }}>
      <div style={{ position: 'relative', overflow: 'hidden', height: `${wrapperHeight}px` }}>
        <div ref={wrapperRef} className="wrapper">
          <div className="header">
            <div className="btn-group1 px-1">
              <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.ios} title="IOS">
                <img className="img-fluid brightness" alt="IOS" src="https://kpahplus.top/homepage/images/btn-download-ios-header.png" />
              </a>
              <a className="px-1 zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.apk} title="APK">
                <img className="img-fluid brightness" alt="APK" src="https://kpahplus.top/homepage/images/btn-download-apk-header.png" />
              </a>
            </div>
            <div className="btn-group2 px-1">
              <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.jar} title="JAR">
                <img className="img-fluid brightness" alt="JAR" src="https://kpahplus.top/homepage/images/btn-download-jar-header.png" />
              </a>
              <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.pc} title="PC">
                <img className="img-fluid brightness" alt="PC" src="https://kpahplus.top/homepage/images/btn-download-pc-header.png" />
              </a>
            </div>
            <a className="px-1 zoom-50 auth-tile" href="/login" title="Đăng nhập">
              <span>Đăng</span>
              <span>nhập</span>
            </a>
            <a className="px-1 zoom-50 auth-tile auth-tile-register" href="/register" title="Đăng ký">
              <span>Đăng</span>
              <span>ký</span>
            </a>
          </div>

          <section id="section1" className="section section1">
            <div className="section-background">
              <img
                className="img-fluid desktop"
                alt="Background hero desktop"
                src="https://kpahplus.top/homepage/images/bg-page1.png"
                style={{ display: 'block', width: '100%' }}
              />
              <img
                className="img-fluid mobile"
                alt="Background hero mobile"
                src="https://kpahplus.top/homepage/images/bg-page1-m.jpg"
                style={{ display: 'block', width: '100%' }}
              />
            </div>
            <div className="section-content">
              <div className="art desktop">
                <img className="img-fluid" alt="Khí Phách Anh Hùng logo" src="/logo/logo.png" />
              </div>
              <div className="art2 mobile">
                <img className="logo" alt="Khí Phách Anh Hùng logo mobile" src="/logo/logo.png" />
              </div>
              <div className="btn-group-download">
                <a className="px-1 zoom-50" href="/" title="KPAH">
                  <img className="img-fluid brightness" alt="KPAH app icon" src="https://kpahplus.top/homepage/images/icon-app-plus.png" />
                </a>
                <div className="btn-group1 px-1">
                  <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.ios} title="IOS">
                    <img className="img-fluid brightness" alt="Download IOS" src="https://kpahplus.top/homepage/images/btn-download-ios-page1.png" />
                  </a>
                  <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.apk} title="APK">
                    <img className="img-fluid brightness" alt="Download APK" src="https://kpahplus.top/homepage/images/btn-download-apk-header.png" />
                  </a>
                </div>
                <div className="btn-group2 px-1">
                  <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.jar} title="JAR">
                    <img className="img-fluid brightness" alt="Download JAR" src="https://kpahplus.top/homepage/images/btn-download-jar-page1.png" />
                  </a>
                  <a className="zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.pc} title="PC">
                    <img className="img-fluid brightness" alt="Download PC" src="https://kpahplus.top/homepage/images/btn-download-pc-page1.png" />
                  </a>
                </div>
                <a className="px-1 zoom-50 auth-tile" href="/login" title="Đăng nhập">
                  <span>Đăng</span>
                  <span>nhập</span>
                </a>
                <a className="px-1 zoom-50 auth-tile auth-tile-register" href="/register" title="Đăng ký">
                  <span>Đăng</span>
                  <span>ký</span>
                </a>
              </div>
            </div>
            <div className="light">
              <img alt="" src="https://kpahplus.top/homepage/images/light.png" />
              <img alt="" src="https://kpahplus.top/homepage/images/light.png" />
            </div>
          </section>

          <section id="section2" className="section section2">
            <div className="section-background">
              <img
                className="img-fluid desktop"
                alt="Background section 2 desktop"
                src="https://kpahplus.top/homepage/images/bg-page2.png"
                style={{ display: 'block', width: '100%' }}
              />
              <img
                className="img-fluid mobile"
                alt="Background section 2 mobile"
                src="https://kpahplus.top/homepage/images/bg-page2-m.jpg"
                style={{ display: 'block', width: '100%' }}
              />
            </div>
            <div className="section-content">
              <h2 className="title-page2">
                <img className="img-fluid" alt="Lịch hoạt động - Sự kiện" src="https://kpahplus.top/homepage/images/title-page2.png" />
              </h2>
              <div className="posts-content">
                <div className="btn-h">
                  <div className={`select-tin${activeTab === 'new' ? ' active' : ''}`} onClick={() => setActiveTab('new')}>
                    BÀI ĐĂNG MỚI
                  </div>
                  <div className={`select-tin${activeTab === 'feature' ? ' active' : ''}`} onClick={() => setActiveTab('feature')}>
                    TÍNH NĂNG
                  </div>
                  <div className={`select-tin${activeTab === 'guide' ? ' active' : ''}`} onClick={() => setActiveTab('guide')}>
                    HƯỚNG DẪN
                  </div>
                </div>
                <div className="btn-h2" id="btn-h22">
                  {getActivePosts().length > 0 ? (
                    getActivePosts().map((post) => (
                      <div key={post.id} className="post2">
                        <a href={`/post/${post.id}`}>{post.title}</a>
                      </div>
                    ))
                  ) : (
                    <div className="post2">
                      <a href="/#section2">Chuyên mục này chưa có bài viết nào.</a>
                    </div>
                  )}
                </div>
              </div>
            </div>
            <div className="art-page2">
              <img className="img-fluid" alt="Character art" src="https://kpahplus.top/homepage/images/art-page2.png" />
            </div>
          </section>

          <section id="section3" className="section section3">
            <div className="section-background">
              <img
                className="img-fluid mobile"
                alt="Background footer mobile"
                src="https://kpahplus.top/homepage/images/bg-page3-m.jpg"
                style={{ display: 'block', width: '100%' }}
              />
            </div>
            <div className="section-content">
              <div className="footer-content">
                <div className="footer-text font-size-16"></div>
              </div>
            </div>
          </section>

          <div className="bg-bottom-img">
            <img
              className="img-fluid"
              alt="Background bottom decoration"
              src="https://kpahplus.top/homepage/images/bg-page.png"
              style={{ display: 'block', width: '100%' }}
            />
          </div>
        </div>
      </div>

      <div className="download">
        <a className="btn-img zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.ios} role="button" title="Tải Game IOS">
          <span className="visually-hidden">Tải Game IOS</span>
          <img className="img-fluid brightness" alt="Download IOS" src="https://kpahplus.top/homepage/images/btn-download-ios-right.png" />
        </a>
        <a className="btn-img zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.jar} role="button" title="Tải Game Jar">
          <span className="visually-hidden">Tải Game Jar</span>
          <img className="img-fluid brightness" alt="Download JAR" src="https://kpahplus.top/homepage/images/btn-download-jar-right.png" />
        </a>
        <a className="btn-img zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.apk} role="button" title="Tải APK">
          <span className="visually-hidden">Tải Game APK</span>
          <img className="img-fluid brightness" alt="Download APK" src="https://kpahplus.top/homepage/images/btn-download-apk-right.png" />
        </a>
        <a className="btn-img zoom-50" target="_blank" rel="noopener noreferrer" href={downloadLinks.pc} role="button" title="Tải Game PC">
          <span className="visually-hidden">Tải Game PC</span>
          <img className="img-fluid brightness" alt="Download PC" src="https://kpahplus.top/homepage/images/btn-download-pc-right.png" />
        </a>
      </div>
    </div>
  );
}
