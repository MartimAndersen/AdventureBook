## Prerequisites

- Docker Desktop
- Java 25

## Start PostgreSQL

From this directory, start the database:

```powershell
docker compose up -d
```

The Compose service creates the `adventure_book` database and exposes it on
`localhost:5432`. The database files are stored in the named
`adventure_book_postgres_data` volume, so saved games remain available when the
container is stopped or recreated.

To stop the database while keeping its data:

```powershell
docker compose down
```

To remove the database and all saved games as well, explicitly remove the
volume:

```powershell
docker compose down -v
```

## Start the backend

Start PostgreSQL first, then run the backend with the Maven Wrapper:

```powershell
docker compose up -d
.\mvnw.cmd spring-boot:run
```

## Start the frontend

cd ../frontend
npm install
npm start

The frontend will be available at:
http://localhost:4200