{{- define "gateway-service.fullname" -}}
{{- default .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "gateway-service.name" -}}
{{ .Values.app }}
{{- end }}

{{- define "gateway-service.labels" -}}
app: k8s-gateway-service
{{- end }}