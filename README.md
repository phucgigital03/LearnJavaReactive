# Java Reactive Programming - Project Code

This repository contains sample code from the "Java Reactive Programming" course (Project Reactor / Reactor-style examples). It is intended as learning material and small demos.

## Overview

Reactive programming (Project Reactor, Spring WebFlux) is designed for one specific goal: handling massive concurrency with very few resources. It is not a "magic tool" for everything — for simple apps it often adds unnecessary complexity. For the right scenarios, however, it's a game-changer.

## What is it useful for? (The "Sweet Spot")

Reactive programming is most useful for I/O-intensive applications where the server spends most of its time waiting for something else (a database, an API, or a file).

| Use Case                | Status   | Why?                                                                                     |
|-------------------------|----------|------------------------------------------------------------------------------------------|
| Microservices / Gateways| ✅ Perfect | A gateway calls many services and spends most time waiting for replies; reactive lets a few threads handle many waiting requests. |
| Streaming Apps          | ✅ Perfect | Netflix/Spotify/chat apps where data flows continuously.                                 |
| High-Traffic APIs       | ✅ Perfect | Handles large numbers of concurrent connections economically.                             |
| Data Processing         | ✅ Good    | Processing large files line-by-line without loading everything into memory.              |
| Simple CRUD App         | ❌ Overkill| For small internal tools with low load, use Spring MVC — simpler.                       |
| CPU Heavy Tasks         | ❌ Bad     | Image/video processing or heavy math will block shared threads and hurt concurrency.    |

### The "Restaurant" Analogy (Why it works)

Traditional Java (thread-per-request): one waiter per customer. If a waiter must wait in the kitchen (blocking), you need many waiters (threads) for many customers.

Reactive Java (non-blocking): one fast waiter takes orders, submits them, and moves on. When the food is ready the kitchen notifies the waiter (an event/callback) who then delivers it. Result: far fewer waiters (threads) handle many customers.

## When should you learn/use it?

Apply it for: building high-performance microservices, API gateways, or systems that need to handle thousands of concurrent connections (chat servers, stock tickers).

Don't apply it for: heavy CPU-bound work (math, graphics) or small internal apps where code simplicity is more important than raw concurrency.