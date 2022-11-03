{{- define "comit-admin.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "comit-admin.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "comit-admin.labels" -}}
app: {{ .Values.app }}
{{- end }}