USE [master]
GO

IF DB_ID('spring_boot') IS NOT NULL
  set noexec on

CREATE DATABASE [spring_boot];
GO