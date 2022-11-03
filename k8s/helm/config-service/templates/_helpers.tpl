{{- define "config-service.fullname" -}}
{{ .Chart.Name }}
{{- end }}

{{- define "config-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "config-service.labels" -}}
app: {{ .Values.app }}
{{- end }}
