{{- define "history-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "history-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "history-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
