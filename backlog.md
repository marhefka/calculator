# Calculator

First goal: entering digits, addition, equals sign

Backlog: 

- starting new calculation after equals sign
- continue calculation after equals sign
- subtraction
- unary - operator
- multiplication  
- division
- Pressing two operation signs after one another?
- Handling overflow
- saving & loading calculator
- Concurrent usage




First goal: entering digits, addition, equals sign

Scenario:

1. {0} 35 + 4 = {39}

## API design

### Starting a new calculator

```json
/calculator PUT
{
}
```

Response:

```
{
    id: "id945"
}
```

### Pressing a button

```json
/calculator?id=1342345 PUT
{
    buttonPressed: "5"
}
```

Response:

```json
{
  id: "id945",
  display: "5"
}
```

