{{- define "organization-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "organization-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "organization-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
