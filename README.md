## Fraud Detection Login Attempts System
### Overview

This project was created with the goal of studying and applying core Java (Spring) concepts, event-driven architecture, and fraud detection mechanisms in a realistic backend system.
The system simulates a login attempts pipeline, where each authentication attempt is processed, analyzed, and evaluated for potential fraudulent behavior using events, caching, and asynchronous processing.

It is designed as a learning-oriented but production-inspired architecture, focusing on scalability, decoupling, and observability.

## Goals of the Project

- Practice Java & Spring Boot in a real-world scenario

- Apply Event-Driven Architecture concepts

- Model and process fraud-related events

- Understand idempotency, retries, and async processing

- Use cache and messaging systems to improve performance

- Implement observability using metrics

## Core Concepts Covered

- Event-driven communication

- Asynchronous processing

- Fraud detection rules

- Rate limiting and login attempt analysis

Distributed system design fundamentals

- System Modeling
- Database Model

The database schema was designed to support authentication events, login attempts, and fraud analysis.

View the database model:
https://dbdiagram.io/d/698790a8bd82f5fce2fbad33

## Macro Architecture

The macro architecture follows an event-oriented approach, where login attempts generate events that are processed asynchronously by different services and components.

Architecture Highlights

Services are loosely coupled

Events are propagated through a message broker

Fraud analysis happens asynchronously

Caching is used to reduce database load

Metrics are exposed for monitoring and analysis

You view an image below<img width="1782" height="891" alt="Login attempts system" src="https://github.com/user-attachments/assets/1a464161-87e1-435e-90c1-4aa13b49605f" />

## Technologies Used

Backend & Infrastructure

- Java – Core language used for the backend services

- Spring Boot – Application framework and dependency management

- PostgreSQL – Relational database for persistent data

- Redis – In-memory cache for fast access and rate-limiting scenarios

- RabbitMQ – Message broker for event-driven communication

- Prometheus – Metrics collection and monitoring

## Observability & Monitoring

- Application metrics are exposed for Prometheus scraping

- Fraud-related indicators can be tracked over time

- Enables future integration with dashboards (e.g., Grafana)
