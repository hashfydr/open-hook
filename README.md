# Open-Hook 🪝

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
Open-Hook is an open-source, ultra-lightweight Webhook Inspector built for developers. It provides a real-time, zero-configuration temporary mailbox to instantly catch and inspect HTTP requests, making third-party webhook integrations (like Stripe, GitHub, or Twilio) completely painless to test locally.

## Why Open-Hook?

When building integrations that rely on webhooks, testing locally can be a frustrating experience. Developers often hit paywalls or usage limits on popular services like Ngrok or Webhook.site just to inspect the structure of an incoming JSON payload. 

Open-Hook solves this by giving you a completely free, self-hostable alternative. With a single click, it generates a unique URL endpoint. The moment a service fires an HTTP request to that URL, the raw headers, body, and method are instantly piped to your browser dashboard.

## Features

- **Real-Time Delivery:** Utilizes Server-Sent Events (SSE) to push incoming payloads instantly to the UI without requiring page refreshes.
- **Dynamic Catch-All API:** Intercepts `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, and `OPTIONS` requests seamlessly.
- **Zero Disk I/O:** Uses an ultra-fast H2 in-memory database to store temporary payloads, meaning no bloated databases and automatic cleanup on server restart.
- **Lightweight UI:** The dashboard is built with Vanilla JS and Tailwind CSS for maximum speed—no heavy React bundles.

## Tech Stack

- **Backend:** Java 17, Spring Boot 3
- **Database:** H2 (In-Memory)
- **Frontend:** HTML, Vanilla JavaScript, Tailwind CSS (via CDN)
- **Deployment:** Docker

## Getting Started

### Option 1: Running Locally (Maven)

Ensure you have **Java 17** installed and your `JAVA_HOME` environment variable configured.

1. Clone the repository:
   ```bash
   git clone https://github.com/hashfydr/open-hook.git
   cd open-hook
   ```
2. Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Open your browser and navigate to `http://localhost:8080`.

### Option 2: Running via Docker

Open-Hook includes a multi-stage `Dockerfile` making it trivial to run anywhere.

1. Build the image:
   ```bash
   docker build -t open-hook .
   ```
2. Run the container:
   ```bash
   docker run -p 8080:8080 open-hook
   ```

## Usage

1. Open the dashboard at `http://localhost:8080`.
2. The UI will automatically generate a unique Webhook URL for you (e.g., `http://localhost:8080/api/webhook/123e4567-e89b-12d3-a456-426614174000`).
3. Copy this URL and configure your third-party service (or Postman) to send requests to it.

**Example Test Request:**
```bash
curl -X POST http://localhost:8080/api/webhook/YOUR_UNIQUE_UUID \
     -H "Content-Type: application/json" \
     -H "X-Custom-Header: Open-Hook-Test" \
     -d '{"message": "Testing real-time delivery!", "status": "success"}'
```

Watch the dashboard—the JSON payload will instantly appear on your screen!

## License

This project is licensed under the MIT License - see the LICENSE file for details.
