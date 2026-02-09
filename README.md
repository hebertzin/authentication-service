# Authentication & Login Attempts System

## Overview

This project was created to study and apply core Java (Spring Boot) concepts, event-driven architecture, and fraud detection mechanisms in a realistic backend system.

It simulates an authentication pipeline where each login attempt can be processed, analyzed, and evaluated for potential fraudulent behavior using device recognition, trust levels, and (future) asynchronous event processing.

Although learning-oriented, the architecture and design decisions are production-inspired, focusing on scalability, decoupling, and observability.

---

## Goals of the Project

- Practice Java & Spring Boot in a real-world scenario
---

## Core Concepts Covered

- Device recognition & device trust model
- Deterministic fingerprint generation (HMAC-SHA256)
- Trust-based login foundation (UNTRUSTED/TRUSTED/BLOCKED concept)
- Basic anti-abuse controls (device limit per user)
- Foundations for event-driven communication (planned next)
---

## System Modeling

### Database Model

The database schema was designed to support authentication attempts, device tracking, and fraud analysis.

Database model:
- https://dbdiagram.io/d/698790a8bd82f5fce2fbad33

---

## Current Features (Implemented)

### Device Management (Create/Find)

When a user tries to authenticate, the system resolves the current device:

- Generates a deterministic `fingerprintHash` based on:
    - `userAgent`
    - `platform`
    - `deviceType`
- Looks up an existing device by `(userId, fingerprintHash)`
- If found:
    - updates metadata (e.g. last IP, platform, user agent)
- If not found:
    - creates a new device with default trust level `UNTRUSTED`

This enables the system to identify returning devices and maintain device history for fraud analysis.

### Device Trust Level (foundation)

Devices have a trust level field:

- `UNTRUSTED` (default for newly created devices)

This project currently stores the trust state for each device, enabling future steps like:
- allowing login only from trusted devices
- enforcing step-up verification (OTP/MFA) for untrusted devices

###  Device Limit Per User (Anti-abuse)

To prevent uncontrolled device creation (which can be abused to inflate state or bypass policies), the system enforces:

- `MAX_DEVICES_ALLOW = 5`

If the user exceeds the limit, a `BadRequestException` is thrown.

---

## Fingerprint: How Device Recognition Works

This project implements a deterministic fingerprint mechanism:

### What is a fingerprint?

A fingerprint is a stable identifier generated from device signals. The goal is to answer:

> "Is this the same device I’ve seen before?"

### How it is generated in this project

The system normalizes the main device attributes:

- trimming whitespace
- converting to lowercase
- joining the parts with `|`

Then it generates a fingerprint hash using **HMAC-SHA256**, which is a keyed hash:

- fingerprint = `HMAC_SHA256(SECRET, normalized_data)`

This approach is useful because:

- it is deterministic (same input → same output)
- it does not expose raw device data in the database
- it is harder to spoof than plain hashing without a secret

### Important note about the SECRET

Today the secret is hardcoded:

private static final String SECRET = "some-secret";

## Macro Architecture

The macro architecture follows an event-oriented approach, where login attempts generate events that are processed asynchronously by different services and components.

- Architecture Highlights

- Services are loosely coupled

- Events are propagated through a message broker

- Fraud analysis happens asynchronously

- Caching is used to reduce database load

- Metrics are exposed for monitoring and analysis

## Technologies Used

Backend & Infrastructure

- Java – Core language used for the backend services

- Spring Boot – Application framework and dependency management

- PostgreSQL – Relational database for persistent data

## Observability & Monitoring

- Application metrics are exposed for Prometheus scraping

- Fraud-related indicators can be tracked over time

- Enables future integration with dashboards (e.g., Grafana)
