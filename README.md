# 🎫 Concurrent Ticket System Advanced

A **production-grade** Java concurrent ticket printing system showcasing the complete evolution from basic multithreading concepts to **enterprise-level patterns** with fault tolerance, monitoring, and external configuration.

## 🎯 Overview

This project demonstrates a **real-world concurrent system** where multiple passengers (threads) print tickets while technicians (threads) manage resources, featuring **advanced patterns** like Circuit Breaker, Operation Timeouts, and Real-time Monitoring.

**🏗️ Two Complete Implementations:**
- **📚 `src/basic/`** - Core concurrency learning (7 classes)
- **🚀 `src/advanced/`** - Production-grade enterprise patterns (10+ classes)

**✨ What Makes This Special:**
- **Circuit Breaker Pattern** for fault tolerance
- **External Configuration** management
- **Advanced Ticket Types** with dynamic pricing
- **Operation Timeouts** with retry logic
- **Real-time Monitoring** dashboard
- **Professional Logging** framework

## 🔧 Key Concurrency Concepts

- **ReentrantLock**: For mutual exclusion and thread-safe operations
- **Condition Variables**: For thread coordination (wait/signal pattern)
- **Producer-Consumer Pattern**: Passengers consume resources while technicians produce them
- **Thread Groups**: Organized thread management
- **Atomic Operations**: Thread-safe global ticket counter using `AtomicInteger`

## 📋 Two Complete Versions

### 📚 **Basic Version** (`src/basic/`)
**Perfect for learning core concepts**
- **`TicketPrintingSystem`**: Simple thread management
- **`TicketMachine`**: ReentrantLock + Condition variables
- **`Passenger`**: Basic consumer thread
- **`TicketPaperTechnician`** & **`TicketTonerTechnician`**: Resource producers
- **Focus**: Thread synchronization, Producer-Consumer pattern

### 🚀 **Advanced Version** (`src/advanced/`)  
**Enterprise-grade implementation with production patterns**
- **`TicketPrintingSystemAdvanced`**: ExecutorService thread pools + graceful shutdown
- **`TicketMachineAdvanced`**: Circuit breaker integration + timeout handling
- **`CircuitBreaker`**: Fault tolerance with CLOSED/OPEN/HALF_OPEN states
- **`OperationTimeout`**: Timeout protection with exponential backoff retry
- **`SystemConfiguration`**: External config management with fallback defaults
- **`TicketType`**: Advanced ticket types (Economy/Business/First/VIP) with dynamic costs
- **`SimpleLogger`**: Professional logging with levels and timestamps
- **`TicketSystemMonitor`**: Real-time performance dashboard + comprehensive reporting
- **`PassengerPriority`**: VIP/Business/Economy classification system
- **Focus**: Fault tolerance, Configuration management, Production monitoring

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- VSCode/Cursor with Java Extension Pack (or any Java IDE)

### 🎯 Quick Start - VSCode/Cursor
1. Press `F5` or click "Run"
2. Choose your version:
   - **🚀 Run Advanced System (Recommended)** - See production patterns
   - **📚 Run Basic System** - Learn core concepts

### 💻 Terminal Commands
```bash
# Compile both versions
javac -d bin src/basic/*.java src/advanced/*.java

# Run basic version (learning)
java -cp bin TicketPrintingSystem

# Run advanced version (production features)
java -cp bin TicketPrintingSystemAdvanced
```

## 📊 Example Output

### 🚀 Advanced Version Output:
```
[INFO] Configuration loaded from config.properties
[INFO] === SYSTEM CONFIGURATION ===
[INFO] Circuit Breaker - Failure Threshold: 5

+----------------------------------------------------------+
| *** REAL-TIME SYSTEM STATISTICS ***                      |
+----------------------------------------------------------+
| Runtime: 15s | Throughput: 0.80 tickets/sec              |
| Total Tickets: 12 | Avg Wait: 0.15s                     |
+----------------------------------------------------------+
| VIP: 3 | Business: 4 | Economy: 5                      |
+----------------------------------------------------------+

[SUCCESS] Printed VIP Premium - Consumed: Toner=20, Paper=4 | Price: $750.00
[INFO] Circuit breaker CLOSED - service recovered!

*** FINAL SYSTEM REPORT ***
Total Runtime: 30 seconds | Average Throughput: 0.83 tickets/second
VIP: 20% | Business: 40% | Economy: 40%
```

### 📚 Basic Version Output:
```
- - - - - Ticket Printed - - - - -
Ticket printed by Passenger 1
Ticket No : 0001 , Ticket Price : 100.0

= = = = = Ticket Machine Final Status = = = = =
    ticketsPrinted = 10, tonerLevel = 50, paperLevel = 140
```

## 🎓 Learning Outcomes

### 🔧 **Core Concurrency Concepts:**
- **Thread Synchronization**: ReentrantLock + Condition variables
- **Producer-Consumer Pattern**: Resource sharing with coordination
- **Atomic Operations**: Lock-free programming with AtomicInteger
- **Thread Lifecycle Management**: Proper startup and graceful shutdown

### 🚀 **Advanced Enterprise Patterns:**
- **Circuit Breaker Pattern**: Fault tolerance and cascading failure prevention
- **Timeout & Retry Logic**: Resilience with exponential backoff
- **External Configuration**: Properties-driven system settings
- **Thread Pool Management**: ExecutorService for scalable threading
- **Real-time Monitoring**: Performance metrics and system observability
- **Structured Logging**: Professional debugging and operational visibility

## 🛠️ Technical Details

### Synchronization Strategy
- Uses `ReentrantLock` for mutual exclusion
- Three `Condition` variables:
  - `refillToner`: Signals when toner needs refilling
  - `refillPaper`: Signals when paper needs refilling
  - `resourcesLacking`: Signals when resources are available

### Resource Limits
- Full Paper Tray: 250 sheets
- Full Toner Level: 100 units
- Minimum Toner Level: 10 units
- Paper Pack Size: 50 sheets
- Toner per Ticket: 5 units
- Paper per Ticket: 1 sheet

## 📊 Feature Comparison

| Feature | 📚 Basic | 🚀 Advanced |
|---------|----------|-------------|
| **Thread Management** | Manual `new Thread()` | ExecutorService pools + graceful shutdown |
| **Fault Tolerance** | None | Circuit Breaker pattern |
| **Configuration** | Hardcoded values | External config.properties |
| **Timeout Handling** | None | 5s timeout + 3 retries with backoff |
| **Logging** | `System.out.println()` | Structured logging with levels |
| **Monitoring** | None | Real-time dashboard + final report |
| **Ticket Types** | Single type | 4 types (Economy/Business/First/VIP) |
| **Pricing** | Fixed | Dynamic with priority multipliers |
| **Resource Management** | Fixed consumption | Dynamic based on ticket type |
| **Files** | 7 classes | 10+ classes |
| **Production Ready** | ❌ Learning only | ✅ Enterprise-grade |

## 📚 Documentation

- **[FEATURES_GUIDE.md](FEATURES_GUIDE.md)** - **📖 Complete features guide** - Why each feature exists, implementation details, and production benefits
- **[ADVANCED_FEATURES_SUMMARY.md](ADVANCED_FEATURES_SUMMARY.md)** - Quick overview of advanced capabilities
- **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)** - Navigation guide and architecture comparison

## 📝 Future Enhancements

### ✅ **Already Implemented:**
- ✅ Different ticket types with varying resource costs (Economy/Business/First/VIP)
- ✅ Timeout and retry mechanisms with exponential backoff
- ✅ Circuit Breaker pattern for fault tolerance
- ✅ External configuration management
- ✅ Real-time monitoring dashboard
- ✅ Professional logging framework

### 🔮 **Potential Future Additions:**
- [ ] Implement actual priority scheduling with PriorityBlockingQueue
- [ ] Add comprehensive unit test suite with JUnit
- [ ] JMX integration for enterprise monitoring tools
- [ ] Web-based monitoring dashboard
- [ ] Database persistence for ticket history
- [ ] Load testing framework with configurable scenarios
- [ ] Metrics export to Prometheus/Grafana
- [ ] Docker containerization with health checks

## 👨‍💻 Author

**Munaza** - *Software Engineering Student*

- 🎓 Developed as part of advanced concurrent programming coursework
- 🚀 Enhanced with production-grade enterprise patterns
- 💼 Demonstrates real-world software engineering skills
- 📧 Contact: [Your Email] | 🔗 GitHub: [@Munz10](https://github.com/Munz10)

*This project showcases the evolution from academic learning to professional software development practices.*

## 📄 License

**MIT License**

Copyright (c) 2024 Munaza

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

---

## 🌟 Project Highlights

**This concurrent ticket system demonstrates enterprise-grade software engineering:**

🛡️ **Fault Tolerance** → Circuit Breaker pattern prevents cascading failures  
⚙️ **Configuration Management** → External properties for flexible deployment  
🎫 **Complex Business Logic** → Multiple ticket types with dynamic pricing  
⏰ **Resilience Patterns** → Timeout protection with intelligent retry  
📊 **Production Monitoring** → Real-time dashboard and comprehensive reporting  
🏭 **Professional Threading** → ExecutorService pools with graceful shutdown  
📝 **Structured Logging** → Professional debugging and operational visibility  

**Perfect for portfolios, technical interviews, and demonstrating production-ready code!** ✨

