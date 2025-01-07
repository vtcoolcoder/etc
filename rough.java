private static String[] getSelectedSubjects(HttpServletRequest request) {      
        class RequestParamHelper {
            @Getter
            private final String subject;
            private final String status;
            
            public RequestParamHelper(Map.Entry entry) {
                String[] value = (String[]) entry.getValue(); 
                this.subject = (String) entry.getKey();
                this.status = value.length > 0 ? value[0] : "";
            }
                       
            public boolean filtering() { return "on".equals(status); }       
        }
           
        return request.getParameterMap().entrySet().stream()
                .map(RequestParamHelper::new)
                .filter(RequestParamHelper::filtering)
                .map(RequestParamHelper::getSubject)
                .toArray(String[]::new);
    }