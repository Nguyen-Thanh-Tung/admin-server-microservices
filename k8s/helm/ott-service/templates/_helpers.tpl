{{- define "ott-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ott-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "ott-service.labels" -}}
app: {{ .Values.app }}
{{- end }}