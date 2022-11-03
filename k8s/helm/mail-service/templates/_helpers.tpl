{{- define "mail-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "mail-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "mail-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
