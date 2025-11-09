# Concurrent Ticket System

A Java-based concurrent ticket printing system with **two versions** demonstrating progression from basic to advanced multithreading concepts.

## 🎯 Overview

This project simulates a ticket printing system where multiple passengers (threads) attempt to print tickets concurrently while technicians (threads) refill resources (paper and toner) as needed. 

**📁 Clear Structure:**
- **📚 `src/basic/`** - Core concurrency concepts (7 files)
- **🚀 `src/advanced/`** - Production-grade patterns (10 files)

Each version is **completely self-contained** for easy browsing and learning.

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
**Production-grade implementation**
- **`TicketPrintingSystemAdvanced`**: ExecutorService thread pools
- **`TicketMachineAdvanced`**: Enhanced with logging
- **`PassengerAdvanced`**: Priority-based with monitoring
- **`SimpleLogger`**: Structured logging framework
- **`TicketSystemMonitor`**: Real-time performance dashboard
- **`PassengerPriority`**: VIP/Business/Economy classes
- **Focus**: Thread pools, Monitoring, Professional patterns

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

```
- - - - - Ticket Printed - - - - -
Ticket printed by Passenger 1
Ticket No : 0001 , Ticket Price : 100.0

Paper refilled by Paper Technician. Paper level : 146

Unable to print the ticket due to resource unavailability
Toner refilled by Toner Technician. Toner level : 100

= = = = = Ticket Machine Final Status = = = = =
    ticketsPrinted = 10
    tonerLevel = 50
    paperLevel = 140
```

## 🎓 Learning Outcomes

This project demonstrates:
- Thread synchronization using locks and conditions
- Deadlock prevention strategies
- Resource sharing between multiple threads
- Wait/notify mechanisms for thread coordination
- Thread-safe data structures (AtomicInteger)
- Proper thread lifecycle management

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
| **Thread Management** | Manual `new Thread()` | ExecutorService pools |
| **Logging** | `System.out.println()` | Structured SimpleLogger |
| **Monitoring** | None | Real-time dashboard |
| **Priorities** | None | VIP/Business/Economy |
| **Files** | 7 classes | 10 classes |
| **Learning Focus** | Core concurrency | Production patterns |

## 📚 Documentation

- **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)** - Navigation guide and version comparison
- **[ENHANCEMENTS.md](ENHANCEMENTS.md)** - Deep dive into advanced features  
- **[IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md)** - Complete implementation guide

## 📝 Future Enhancements

- [ ] Implement actual priority scheduling with PriorityBlockingQueue
- [ ] Add different ticket types with varying resource costs
- [ ] Implement timeout and retry mechanisms
- [ ] Add unit tests
- [ ] JMX integration for monitoring tools

## 👨‍💻 Author

Created as part of a coursework assignment on concurrent programming.

## 📄 License

This project is open source and available for educational purposes.

