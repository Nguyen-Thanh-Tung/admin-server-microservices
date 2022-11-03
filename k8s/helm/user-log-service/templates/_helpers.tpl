{{- define "user-log-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "user-log-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "user-log-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
