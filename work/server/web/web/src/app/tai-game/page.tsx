'use client';

import { useEffect, useState } from 'react';

type ReleaseInfo = {
  latestVersion: string;
  downloadUrl: string;
  jarUrl?: string;
  sha256: string;
  jarSha256?: string;
  summary: string;
};

type ReleaseManifest = {
  publishedAt: string;
  channels: {
    pc: ReleaseInfo;
    java: ReleaseInfo;
  };
};

export default function DownloadPage() {
  const [manifest, setManifest] = useState<ReleaseManifest | null>(null);
  const [failed, setFailed] = useState(false);

  useEffect(() => {
    fetch('/downloads/manifest.json', { cache: 'no-store' })
      .then((response) => {
        if (!response.ok) throw new Error('Không tải được danh sách phiên bản.');
        return response.json() as Promise<ReleaseManifest>;
      })
      .then(setManifest)
      .catch(() => setFailed(true));
  }, []);

  return (
    <main className="lan-download-page">
      <section className="lan-download-hero">
        <a className="lan-download-back" href="/">← Trang chủ</a>
        <img src="/logo/logo.png" alt="Khí Phách Anh Hùng" />
        <h1>Tải KPAH trong mạng gia đình</h1>
        <p>Máy tính hoặc điện thoại cần kết nối cùng Wi-Fi/LAN với máy chủ.</p>
        <div className="lan-server-address">Máy chủ: 192.168.110.152:19129</div>
      </section>

      {failed ? (
        <section className="lan-download-error">Chưa đọc được danh sách phiên bản. Hãy tải lại trang sau ít phút.</section>
      ) : !manifest ? (
        <section className="lan-download-error">Đang kiểm tra phiên bản mới nhất…</section>
      ) : (
        <section className="lan-download-grid">
          <article className="lan-download-card">
            <span className="lan-download-platform">WINDOWS PC</span>
            <h2>KPAH PC v{manifest.channels.pc.latestVersion}</h2>
            <p>{manifest.channels.pc.summary}</p>
            <a className="lan-download-button" href={manifest.channels.pc.downloadUrl}>Tải bản PC</a>
            <small>Giải nén rồi chạy <strong>KPAH-Launcher.exe</strong>.</small>
            <code>SHA-256: {manifest.channels.pc.sha256}</code>
          </article>

          <article className="lan-download-card">
            <span className="lan-download-platform">JAVA / FREEJ2ME</span>
            <h2>KPAH Java v{manifest.channels.java.latestVersion}</h2>
            <p>{manifest.channels.java.summary}</p>
            <a className="lan-download-button" href={manifest.channels.java.downloadUrl}>Tải gói Java đầy đủ</a>
            {manifest.channels.java.jarUrl ? (
              <a className="lan-download-secondary" href={manifest.channels.java.jarUrl}>Chỉ tải file JAR</a>
            ) : null}
            <small>Trên Windows, chạy <strong>Start-KPAH-Java.cmd</strong>.</small>
            <code>SHA-256: {manifest.channels.java.sha256}</code>
          </article>
        </section>
      )}

      <section className="lan-update-note">
        <h2>Cập nhật chủ động</h2>
        <p>
          Từ phiên bản này, launcher kiểm tra manifest mỗi lần mở. Khi có bản mới, người chơi được hỏi và có thể tải,
          kiểm tra SHA-256 rồi cài tự động. Nếu máy chủ web tạm thời không truy cập được, bản hiện tại vẫn mở bình thường.
        </p>
      </section>
    </main>
  );
}
