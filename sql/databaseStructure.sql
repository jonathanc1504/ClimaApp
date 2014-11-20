------------------------------------------------------------------
--  TABLE planeta
------------------------------------------------------------------

CREATE TABLE planeta
(
   `PLANETA_ID`       int(10),
   `PLANETA_NOMBRE`   varchar(20),
   `PLANETA_VEL`      float(4, 2),
   `PLANETA_DIST`     float(8, 4)
);


------------------------------------------------------------------
--  TABLE clima
------------------------------------------------------------------

CREATE TABLE clima
(
   `CLIMA_ID`       int(10),
   `CLIMA_ESTADO`   varchar(20),
   `CLIMA_DIA`      int(10)
);