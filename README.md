# Design Patterns - Java

Runnable Java examples for six topics. Each topic has its own folder with **two examples** and a **README** describing the relationships between classes and interfaces (Mermaid class diagrams, rendered automatically by GitHub).

```
.
├── abstract_factory/
│   ├── README.md
│   ├── Example1GuiFactory.java          # cross-platform UI (from the pseudocode)
│   └── Example2FurnitureFactory.java    # furniture shop (from the problem)
├── singleton/
│   ├── README.md
│   ├── Example1Database.java            # database connection (from the pseudocode)
│   └── Example2ConfigManager.java       # shared config, thread-safe
├── factory_method/
│   ├── README.md
│   ├── Example1Dialog.java              # cross-platform dialog (from the pseudocode)
│   └── Example2Logistics.java           # trucks and ships (from the problem)
├── builder/
│   ├── README.md
│   ├── Example1CarBuilder.java          # cars and manuals (from the pseudocode)
│   └── Example2HouseBuilder.java        # houses and castles (from the problem)
├── strategy/
│   ├── README.md
│   ├── Example1Calculator.java          # add / subtract / multiply (from the pseudocode)
│   └── Example2Navigator.java           # route planning (from the problem)
└── mvc/
    ├── README.md
    ├── Example1StudentMvc.java          # student marks (from the explanation)
    └── Example2AtmMvc.java              # ATM withdrawal (from the explanation)
```

In every topic, **Example 1** is the exact example of the explanation (same classes, same names, same flow) and **Example 2** is a second scenario that follows the same structure.

## Requirements

JDK 11 or newer (check with `java -version` and `javac -version`). No external libraries.

## Run everything

From the repository root, using the single-file launcher (no compile step):

```bash
java abstract_factory/Example1GuiFactory.java
java abstract_factory/Example2FurnitureFactory.java
java singleton/Example1Database.java
java singleton/Example2ConfigManager.java
java factory_method/Example1Dialog.java
java factory_method/Example2Logistics.java
java builder/Example1CarBuilder.java
java builder/Example2HouseBuilder.java
java strategy/Example1Calculator.java
java strategy/Example2Navigator.java
java mvc/Example1StudentMvc.java
java mvc/Example2AtmMvc.java
```

Or compile a folder and run the class:

```bash
cd builder
javac *.java
java Example1CarBuilder
```

## Quick comparison

| Topic | Type | Main goal | Mechanism |
|---|---|---|---|
| Factory Method | Creational | Create **one** product; subclasses decide which | Subclass overrides a creation method |
| Abstract Factory | Creational | Create a **family** of related products | A factory object with several creation methods |
| Singleton | Creational | Exactly **one** instance of a class | Private constructor + static `getInstance()` |
| Builder | Creational | Build a complex object **step by step** | Builder steps + optional Director |
| Strategy | Behavioral | **Swap algorithms** at runtime | Context holds a strategy interface reference |
| MVC | Architectural | **Separate responsibilities** of an application | Model (data/logic), View (display), Controller (coordination) |

Each `.java` file has the class with `main` first, followed by the interfaces and classes of the topic. This is only so that `java File.java` can find the entry point; the topic classes are the same as in the explanation.
