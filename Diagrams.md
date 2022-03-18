# Diagrams

```plantuml
@startuml

class Song {
    id: Long
    title: String
    text: String
}

class Phase {
    id: String
}

Song - "*" Phase

@enduml
```
