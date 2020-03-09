import sqlite3
import uuid

from faker import Faker

fake = Faker()

try:
    db = sqlite3.connect("data-index.db")
except:
    print('error - cannot connect to the database')

try:
    for _ in range(10000):
        bulk = "INSERT INTO users (name) VALUES "
        for _ in range(5000):
            id = str(uuid.uuid4())
            name = fake.name().split(" ")[0]
            bulk += f"('{name}'),"
        bulk = bulk.rstrip(",")
        # print(bulk)
        q = db.execute(bulk)
        db.commit()
        q = db.execute("SELECT COUNT(*) FROM users").fetchone()
        print(f"COUNT: {q[0]}")
except:
    print("error - cannot insert")
