{{- define "discovery-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "discovery-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "discovery-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
