# Concurrent Ticket System

A Java-based concurrent ticket printing system demonstrating advanced multithreading concepts and synchronization mechanisms.

## 🎯 Overview

This project simulates a ticket printing system where multiple passengers (threads) attempt to print tickets concurrently while technicians (threads) refill resources (paper and toner) as needed. It showcases proper thread synchronization, deadlock prevention, and resource management.

## 🔧 Key Concurrency Concepts

- **ReentrantLock**: For mutual exclusion and thread-safe operations
- **Condition Variables**: For thread coordination (wait/signal pattern)
- **Producer-Consumer Pattern**: Passengers consume resources while technicians produce them
- **Thread Groups**: Organized thread management
- **Atomic Operations**: Thread-safe global ticket counter using `AtomicInteger`

## 📋 Components

### Core Classes
- **`TicketPrintingSystem`**: Main entry point, creates and manages all threads
- **`TicketMachine`**: Shared resource with synchronized printing operations
- **`Passenger`**: Thread that prints tickets (consumer)
- **`TicketPaperTechnician`**: Thread that refills paper (producer)
- **`TicketTonerTechnician`**: Thread that refills toner (producer)
- **`Ticket`**: Ticket entity with thread-safe numbering
- **`ServiceTicketMachine`**: Interface defining machine operations and constants

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- VSCode/Cursor with Java Extension Pack (or any Java IDE)

### Running in VSCode/Cursor
1. Open the project folder
2. Open `src/TicketPrintingSystem.java`
3. Click the "Run" button above the `main` method (or press F5)

### Running from Terminal
```bash
# Compile
javac -d bin src/*.java

# Run
java -cp bin TicketPrintingSystem
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

## 📝 Future Enhancements

- [ ] Implement priority queue for VIP passengers
- [ ] Add real-time statistics dashboard
- [ ] Implement different ticket types with varying resource costs
- [ ] Add logging framework (SLF4J)
- [ ] Implement timeout and retry mechanisms
- [ ] Add unit tests
- [ ] Create performance monitoring system

## 👨‍💻 Author

Created as part of a coursework assignment on concurrent programming.

## 📄 License

This project is open source and available for educational purposes.

