{{- define "account-service.fullname" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "account-service.name" -}}
{{ $.Values.app }}
{{- end }}

{{- define "account-service.labels" -}}
app: {{ $.Values.app }}
{{- end }}