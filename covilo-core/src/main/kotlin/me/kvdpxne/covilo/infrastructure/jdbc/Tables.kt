package me.kvdpxne.covilo.domain

// table names
const val TABLE_COMMENT = "comment"
const val TABLE_COMMENT_REACTION = "comment_reaction"
const val TABLE_CRIME = "crime"
const val TABLE_CRIME_CLASSIFICATION = "crime_classification"
const val TABLE_CRIME_PERPETRATOR = "crime_perpetrator"
const val TABLE_LOCATION_CITY = "location_city"
const val TABLE_LOCATION_REGION = "location_region"
const val TABLE_LOCATION_COUNTRY = "location_country"

// column names
const val COLUMN_IDENTIFIER = "identifier"
const val COLUMN_KEY = "key"
const val COLUMN_DOMESTIC_NAME = "domestic_name"
const val COLUMN_DATE = "date"
const val COLUMN_EMOJI = "emoji"

const val COLUMN_DESCRIPTION = "description"

// varchar(24)
const val COLUMN_FIRST_NAME = "first_name"
const val COLUMN_LAST_NAME = "last_name"

// unsigned int(10)
const val COLUMN_AGE = "age"

// bool(1)
const val COLUMN_IS_CAUGHT = "is_caught"
const val COLUMN_IS_CONFIRMED = "is_confirmed"

// foreign key column names
const val COLUMN_CRIME = "crime"
const val COLUMN_CLASSIFICATION = "classification"
const val COLUMN_PERPETRATOR = "perpetrator"
const val COLUMN_CITY = "city"
const val COLUMN_REGION = "region"
const val COLUMN_COUNTRY = "country"
