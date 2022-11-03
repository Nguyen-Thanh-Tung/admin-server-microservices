{{- define "time-keeping-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "time-keeping-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "time-keeping-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
