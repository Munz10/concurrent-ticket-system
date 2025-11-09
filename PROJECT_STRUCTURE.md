# Project Structure Guide

## 📁 Clear Organization

This project is organized into **two separate, self-contained versions** for easy browsing and understanding:

```
concurrent-ticket-system/
├── src/
│   ├── basic/                    ← 📚 Simple concurrency version
│   │   ├── TicketPrintingSystem.java
│   │   ├── TicketMachine.java
│   │   ├── Passenger.java
│   │   ├── ServiceTicketMachine.java
│   │   ├── Ticket.java
│   │   ├── TicketTonerTechnician.java
│   │   └── TicketPaperTechnician.java
│   │
│   └── advanced/                 ← 🚀 Professional version
│       ├── TicketPrintingSystemAdvanced.java
│       ├── TicketMachineAdvanced.java
│       ├── PassengerAdvanced.java
│       ├── TonerTechnicianAdvanced.java
│       ├── PaperTechnicianAdvanced.java
│       ├── SimpleLogger.java
│       ├── TicketSystemMonitor.java
│       ├── PassengerPriority.java
│       ├── ServiceTicketMachine.java (shared)
│       └── Ticket.java (shared)
│
├── .vscode/
│   ├── settings.json (configured for both folders)
│   └── launch.json (🚀 Advanced | 📚 Basic)
│
├── bin/ (compiled classes)
├── README.md
├── ENHANCEMENTS.md
├── IMPLEMENTATION_SUMMARY.md
└── PROJECT_STRUCTURE.md (this file)
```

---

## 🎯 Two Complete Versions

### 📚 **Basic Version** (`src/basic/`)
**Perfect for learning core concurrency concepts**

- **Focus**: ReentrantLock, Condition variables, Thread synchronization
- **Classes**: 7 files
- **Complexity**: Beginner to intermediate
- **Output**: Simple console messages
- **Concepts**: Producer-Consumer, Mutual Exclusion, Wait/Signal

**Run:**
```bash
java -cp bin TicketPrintingSystem
```

---

### 🚀 **Advanced Version** (`src/advanced/`)
**Production-grade implementation with monitoring**

- **Focus**: ExecutorService, Logging, Monitoring, Priority systems
- **Classes**: 10 files
- **Complexity**: Intermediate to advanced
- **Output**: Structured logs + real-time dashboard
- **Concepts**: Thread pools, Performance monitoring, Priority queues

**Run:**
```bash
java -cp bin TicketPrintingSystemAdvanced
```

---

## 🔄 Easy Switching

### Using VSCode/Cursor:
1. Press `F5` or click Run
2. Choose your version:
   - **🚀 Run Advanced System (Recommended)** 
   - **📚 Run Basic System**

### Using Terminal:
```bash
# Compile both versions
javac -d bin src/basic/*.java
javac -d bin src/advanced/*.java

# Run basic version
java -cp bin TicketPrintingSystem

# Run advanced version  
java -cp bin TicketPrintingSystemAdvanced
```

---

## 📊 Feature Comparison

| Feature | 📚 Basic | 🚀 Advanced |
|---------|----------|-------------|
| **Thread Management** | Manual `new Thread()` | ExecutorService pools |
| **Logging** | `System.out.println()` | Structured SimpleLogger |
| **Monitoring** | None | Real-time dashboard |
| **Priorities** | None | VIP/Business/Economy |
| **Exception Handling** | Basic try-catch | Centralized via ThreadFactory |
| **Performance Metrics** | None | Throughput, wait times, statistics |
| **Shutdown** | `.join()` calls | Graceful with timeouts |
| **Files** | 7 classes | 10 classes |
| **Learning Focus** | Core concurrency | Production patterns |

---

## 🎓 Suggested Learning Path

### 1. **Start with Basic** 📚
- Understand ReentrantLock and Condition variables
- Learn Producer-Consumer pattern
- Master thread synchronization basics
- See how threads coordinate resource sharing

### 2. **Progress to Advanced** 🚀  
- Learn ExecutorService and thread pools
- Understand structured logging
- Explore performance monitoring
- See production-grade patterns in action

### 3. **Compare & Contrast** 🔍
- Run both versions side by side
- Compare the outputs
- Understand why advanced patterns exist
- Appreciate the evolution from basic to production code

---

## 🛠️ Development Workflow

### Making Changes:

**To Basic Version:**
```bash
cd src/basic
# Edit files
javac -d ../../bin *.java
java -cp ../../bin TicketPrintingSystem
```

**To Advanced Version:**
```bash
cd src/advanced  
# Edit files
javac -d ../../bin *.java
java -cp ../../bin TicketPrintingSystemAdvanced
```

**Both Versions:**
```bash
javac -d bin src/basic/*.java src/advanced/*.java
```

---

## 📖 Documentation Map

| File | Purpose |
|------|---------|
| **README.md** | Main project overview and quick start |
| **ENHANCEMENTS.md** | Deep technical dive into advanced features |
| **IMPLEMENTATION_SUMMARY.md** | Complete implementation guide |
| **PROJECT_STRUCTURE.md** | This file - navigation guide |

---

## 🎯 Why This Organization?

### ✅ **Benefits:**

1. **Clear Separation**: No confusion between versions
2. **Self-Contained**: Each folder has everything needed
3. **Easy Navigation**: Obvious which version you're looking at  
4. **Learning Progression**: Natural path from basic → advanced
5. **Portfolio Ready**: Clean, professional organization
6. **Easy Demonstration**: Can show both versions separately

### ❌ **Alternative (Old Structure):**
```
src/
├── TicketPrintingSystem.java        ← Basic
├── TicketPrintingSystemEnhanced.java ← Advanced  
├── TicketMachine.java               ← Basic
├── TicketMachineEnhanced.java       ← Advanced
└── ... (mixed files - confusing!)
```

**Problems with old structure:**
- Hard to tell which files belong to which version
- "Enhanced" naming is vague  
- Mixed basic and advanced concepts
- Difficult for newcomers to navigate

---

## 🚀 Ready to Explore?

1. **Browse the folders** - See the clear separation
2. **Run both versions** - Compare the outputs  
3. **Read the code** - Notice the progression in complexity
4. **Check documentation** - Each file explains different aspects

**Start with** `src/basic/TicketPrintingSystem.java` **for the foundation!**

---

*This organization makes the project accessible to beginners while showcasing advanced concepts for experienced developers.*
