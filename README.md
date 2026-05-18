# Ride Sharing System

A Java-based ride sharing application demonstrating OOP & SOLID principles with strategy pattern implementation.

## Project Structure

```
src/main/java/com/ridesystem/
├── model/
│   ├── Driver.java
│   ├── FareReceipt.java
│   ├── Ride.java
│   ├── Rider.java
│   ├── RideStatus.java (enum)
│   └── VehicleType.java (enum)
├── service/
│   ├── DriverService.java
│   ├── RideService.java
│   └── RiderService.java
├── strategy/
│   ├── DefaultFareStrategy.java
│   ├── FareStrategy.java (interface)
│   ├── LeastActiveDriverStrategy.java
│   ├── NearestDriverStrategy.java
│   ├── PeakHourFareStrategy.java
│   └── RideMatchingStrategy.java (interface)
└── Main.java
```

## SOLID Principles Demonstrated

- **SRP (Single Responsibility Principle)**: Each service has a single purpose (RiderService, DriverService, RideService)
- **OCP (Open/Closed Principle)**: New ride matching or pricing strategies can be added without modifying core logic
- **LSP (Liskov Substitution Principle)**: Any strategy implementation remains interchangeable
- **ISP (Interface Segregation Principle)**: Small focused interfaces (RideMatchingStrategy, FareStrategy)
- **DIP (Dependency Inversion Principle)**: Services depend on interfaces, not concrete classes

## Design Principles Applied

- **DRY (Don't Repeat Yourself)**: Eliminated duplication in ride allocation logic
- **KISS (Keep It Simple, Stupid)**: Simple entity modeling
- **YAGNI (You Aren't Gonna Need It)**: MVP before feature explosion
- **Law of Demeter**: Services communicate with collaborators directly

## How to Compile and Run

### Compile
```bash
javac src/main/java/com/ridesystem/model/*.java \
      src/main/java/com/ridesystem/strategy/*.java \
      src/main/java/com/ridesystem/service/*.java \
      src/main/java/com/ridesystem/Main.java \
      -d src/main/java
```

### Run
```bash
java -cp src/main/java com.ridesystem.Main
```

## Console Menu Options

1. **Add Rider** - Register a new rider with ID, name, and location
2. **Add Driver** - Register a new driver with ID, name, and current location
3. **View Available Drivers** - List all currently available drivers
4. **Request Ride** - Request a ride for a rider with specified distance
5. **Complete Ride** - Complete a ride and generate fare receipt
6. **View Rides** - View all rides and their status
7. **Exit** - Exit the application

## Strategy Pattern Implementation

### Ride Matching Strategies
- **NearestDriverStrategy**: Matches rider to the nearest available driver
- **LeastActiveDriverStrategy**: Matches rider to the driver with fewest completed rides

### Fare Calculation Strategies
- **DefaultFareStrategy**: Standard fare calculation (base fare + per km rate)
- **PeakHourFareStrategy**: Applies multiplier during peak hours (5 PM - 7 PM)

## Domain Entities

### Rider
- id: String
- name: String
- location: String

### Driver
- id: String
- name: String
- currentLocation: String
- available: boolean
- completedRides: int

### Ride
- id: String
- rider: Rider
- driver: Driver
- distance: double
- status: RideStatus (REQUESTED, ASSIGNED, COMPLETED, CANCELLED)

### FareReceipt
- rideId: String
- amount: double
- generatedAt: LocalDateTime

## Example Usage

1. Add a rider: ID="r1", name="John", location="Downtown"
2. Add a driver: ID="d1", name="Alice", location="Midtown"
3. View available drivers to see the driver list
4. Request a ride for rider "r1" with distance 10 km
5. Assign driver when prompted
6. Complete the ride to see the fare receipt
7. View all rides to see the ride history

## Extensibility

To add a new ride matching strategy:
1. Implement the `RideMatchingStrategy` interface
2. Inject it into `RideService` constructor in `Main.java`

To add a new fare calculation strategy:
1. Implement the `FareStrategy` interface
2. Inject it into `RideService` constructor in `Main.java`
