{{- define "feature-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "feature-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "feature-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
