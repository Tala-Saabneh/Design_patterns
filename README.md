# Design Patterns (Creational) - Java

Runnable Java examples for three creational design patterns. Each pattern has its own folder with **two examples** and a **README** describing the relationships between classes and interfaces (Mermaid class diagrams, rendered automatically by GitHub).

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
└── factory_method/
    ├── README.md
    ├── Example1Dialog.java              # cross-platform dialog (from the pseudocode)
    └── Example2Logistics.java           # trucks and ships (from the problem)
```

In every pattern, **Example 1** is the exact example of the explanation (same classes, same names, same flow) and **Example 2** is a second scenario that follows the same structure.

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
```

Or compile a folder and run the class:

```bash
cd abstract_factory
javac *.java
java Example1GuiFactory
```

## Quick comparison

| Pattern | Creates | Mechanism | Key point |
|---|---|---|---|
| Factory Method | one product | subclass overrides a creation method | creator's main job is business logic, not creation |
| Abstract Factory | a family of related products | a factory object with several creation methods | products from one factory always match |
| Singleton | exactly one object of a class | private constructor + static `getInstance()` | one instance, one global access point |

Each `.java` file has the class with `main` first, followed by the interfaces and classes of the pattern. This is only so that `java File.java` can find the entry point; the pattern classes are the same as in the explanation.
