{{- define "employee-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "employee-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "employee-service.labels" -}}
app: k8s-employee-service
{{- end }}
