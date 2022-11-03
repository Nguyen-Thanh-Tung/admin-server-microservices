{{- define "camera-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "camera-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "camera-service.labels" -}}
app: {{ .Values.app }}
{{- end }}