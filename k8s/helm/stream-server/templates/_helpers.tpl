{{- define "stream-server.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "stream-server.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "stream-server.labels" -}}
app: {{ .Values.app }}
{{- end }}