# React + Spring Boot WebSocket demo

Issue #48 için hazırlanan gerçek zamanlı mesajlaşma demosudur.

WebSocket, istemci ile sunucu arasında tek bağlantıyı açık tutarak iki tarafın da
istediği anda mesaj gönderebildiği çift yönlü bir iletişim protokolüdür. HTTP
polling gibi her mesaj için yeni istek açılması gerekmez.

## Çalıştırma

Önce repository kökünde Spring Boot backend'i başlatın:

```bash
./mvnw spring-boot:run
```

Ardından bu klasörde React uygulamasını başlatın:

```bash
npm install
npm run dev
```

React uygulaması `http://localhost:5173`, WebSocket endpoint'i ise
`ws://localhost:8080/ws/messages` adresinde çalışır.
