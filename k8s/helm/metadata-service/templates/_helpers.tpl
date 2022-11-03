{{- define "metadata-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "metadata-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "metadata-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
