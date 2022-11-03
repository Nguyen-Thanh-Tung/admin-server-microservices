{{- define "area-restriction-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "area-restriction-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "area-restriction-service.labels" -}}
app: {{ .Values.app }}
{{- end }}