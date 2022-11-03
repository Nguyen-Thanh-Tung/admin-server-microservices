{{- define "location-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "location-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "location-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
