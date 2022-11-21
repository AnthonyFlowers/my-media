import os
import pandas as pd
import numpy as np
import mysql.connector

# https://realpython.com/python-data-cleaning-numpy-pandas/

db_username = os.environ["DB_USERNAME"]
db_password = os.environ["DB_PASSWORD"]

mydb = mysql.connector.connect(
  host="localhost",
  user=db_username,
  password=db_password
)

df = pd.read_csv("movies_metadata.csv", low_memory=False)
# print(df.columns)
drop_columns = ["belongs_to_collection", "budget", "genres", "homepage",
                "original_language", "original_title", "popularity", "poster_path",
                "production_companies", "production_countries", "revenue", "spoken_languages",
                "status", "tagline", "video", "vote_average", "vote_count"]
df.drop(drop_columns, inplace=True, axis=1)
# print(df.columns)

df.set_index("id", inplace=True)
# print(df.loc[150])

# Only need the year for my app
print(df["release_date"].head(10))
truncated_dates = df["release_date"].str.extract(r"^(\d{4})", expand=False)
print(truncated_dates.head())
df["release_date"] = pd.to_numeric(truncated_dates)
print(df["release_date"].dtype)

# about 0.2% lose dates
print(df["release_date"].isnull().sum() / len(df))
