import { useEffect, useRef, useState } from "react";

const websocketUrl = import.meta.env.VITE_WEBSOCKET_URL ?? "ws://localhost:8080/ws/messages";

function formatTime(value) {
  return new Intl.DateTimeFormat("tr-TR", {
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  }).format(new Date(value));
}

export default function App() {
  const socketRef = useRef(null);
  const [status, setStatus] = useState("connecting");
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    let active = true;
    let reconnectTimer;

    function connect() {
      setStatus("connecting");
      const socket = new WebSocket(websocketUrl);
      socketRef.current = socket;

      socket.onopen = () => setStatus("online");
      socket.onmessage = (event) => {
        const incomingMessage = JSON.parse(event.data);
        setMessages((current) => [...current, incomingMessage]);
      };
      socket.onerror = () => socket.close();
      socket.onclose = () => {
        if (!active) return;
        setStatus("offline");
        reconnectTimer = window.setTimeout(connect, 2000);
      };
    }

    connect();

    return () => {
      active = false;
      window.clearTimeout(reconnectTimer);
      socketRef.current?.close();
    };
  }, []);

  function sendMessage(event) {
    event.preventDefault();
    const content = message.trim();
    if (!content || socketRef.current?.readyState !== WebSocket.OPEN) return;

    socketRef.current.send(content);
    setMessage("");
  }

  const statusLabel = {
    online: "Bağlantı açık",
    connecting: "Bağlanıyor",
    offline: "Yeniden deneniyor",
  }[status];

  return (
    <main className="shell">
      <aside className="intro">
        <p className="kicker">SPRING BOOT × REACT</p>
        <h1>Canlı<br />hat</h1>
        <p className="description">
          Tarayıcı ve sunucu arasında açık kalan çift yönlü bağlantı. Mesaj gönder;
          cevap yeni bir HTTP isteği olmadan anında gelsin.
        </p>

        <div className={`connection ${status}`}>
          <span className="pulse" aria-hidden="true" />
          <div>
            <strong>{statusLabel}</strong>
            <small>{websocketUrl}</small>
          </div>
        </div>

        <dl>
          <div><dt>Protokol</dt><dd>WebSocket</dd></div>
          <div><dt>Aktarım</dt><dd>Çift yönlü</dd></div>
          <div><dt>Endpoint</dt><dd>/ws/messages</dd></div>
        </dl>
      </aside>

      <section className="console">
        <header>
          <div>
            <span>CANLI AKIŞ</span>
            <strong>{messages.length} mesaj</strong>
          </div>
          <span className="signal" aria-label={statusLabel} />
        </header>

        <div className="message-list" aria-live="polite">
          {messages.length === 0 ? (
            <div className="empty-state">
              <span>↗</span>
              <p>Hat hazır olduğunda ilk mesajını gönder.</p>
            </div>
          ) : (
            messages.map((item, index) => (
              <article key={`${item.sentAt}-${index}`}>
                <div className="message-meta">
                  <span>istemci/{item.sender}</span>
                  <time dateTime={item.sentAt}>{formatTime(item.sentAt)}</time>
                </div>
                <p>{item.message}</p>
              </article>
            ))
          )}
        </div>

        <form onSubmit={sendMessage}>
          <label htmlFor="message">Yeni mesaj</label>
          <div className="composer">
            <input
              id="message"
              maxLength="300"
              value={message}
              onChange={(event) => setMessage(event.target.value)}
              placeholder="Hatta bir mesaj bırak…"
              autoComplete="off"
            />
            <button disabled={status !== "online" || !message.trim()} type="submit">
              Gönder <span aria-hidden="true">↗</span>
            </button>
          </div>
        </form>
      </section>
    </main>
  );
}
