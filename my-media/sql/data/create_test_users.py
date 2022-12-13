import os, mysql.connector

# create a script that adds the default test users
# johnsmith - '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'
# janedoe - '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'

# connection variables
db_username = os.environ["DB_USERNAME"]
db_password = os.environ["DB_PASSWORD"]
host = "localhost"
database = "my_media"

# create connection
mysql_conn = mysql.connector.connect(
    host=host,
    user=db_username,
    password=db_password,
    database=database
)

# use the connection to create a cursor
cursor = mysql_conn.cursor()

# create sql statement
sql = """
insert into app_user (username, password_hash) values
    ('johnsmith', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'),
    ('janedoe', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'),
    ('ashketchum', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa');
"""
# execute sql statement
cursor.execute(sql)
mysql_conn.commit()
cursor.execute("""
insert into app_user_role (app_user_id, app_role_id) values
    (1, 1),
    (2, 2);
""")
mysql_conn.commit()

# close cursor and connection
cursor.close()
mysql_conn.close()