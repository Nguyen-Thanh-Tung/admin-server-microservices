mkdir -p publish &&
helm package account-service -d publish &&
helm package area-restriction-service -d publish &&
helm package camera-service -d publish &&
helm package employee-service -d publish &&
helm package feature-service -d publish &&
helm package gateway-service -d publish &&
helm package history-service -d publish &&
helm package location-service -d publish &&
helm package mail-service -d publish &&
helm package metadata-service -d publish &&
helm package organization-service -d publish &&
helm package time-keeping-service -d publish &&
helm package user-log-service -d publish &&
helm package discovery-service -d publish &&
helm package config-service -d publish &&
helm package stream-server -d publish &&
helm package ott-service -d publish &&
helm package comit-admin -d publish