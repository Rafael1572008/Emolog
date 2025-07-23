## 📊 Diagrama do Banco de Dados (Mermaid)

### Tabelas principais:

- `usuarios`: contém os dados de autenticação
- `registros_humor`: cada entrada de humor registrada pelo usuário
- `tags`: categorias definidas para os registros
- `registro_tag`: relação N:N entre registros e tags

### Relações entre tabelas

- `usuarios` → `registros_humor` (1:N): um usuário pode ter vários registros de humor
- `registros_humor` ↔ `tags` (N:N via `registro_tag`): um registro pode ter várias tags, e uma tag pode estar em vários registros

```mermaid
erDiagram
    usuarios ||--o{ registros_humor : possui
    registros_humor ||--o{ registro_tag : contem
    tags ||--o{ registro_tag : categoriza

    usuarios {
        int id PK
        text nome
        text email
        text senha_hash
    }

    registros_humor {
        int id PK
        int usuario_id FK
        text humor
        text texto
        datetime data_hora
    }

    tags {
        int id PK
        text nome
    }

    registro_tag {
        int registro_id FK
        int tag_id FK
    }
```