<%--@elvariable id="chatSessionId" type="long"--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat">
    <jsp:attribute name="extraHeadContent">
        <link rel="stylesheet"
              href="<c:url value="/resource/stylesheet/chat.css" />" />
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="chatContainer">
            <div id="chatLog">

            </div>
            <div id="messageContainer">
                <textarea id="messageArea"></textarea>
            </div>
            <div id="buttonContainer">
                <button class="btn btn-primary" onclick="send();"><spring:message code="button.chat.send" /></button>
                <button class="btn" onclick="disconnect();"><spring:message code="button.chat.disconnect" /></button>
            </div>
        </div>
        <div id="modalError" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3><spring:message code="window.chat.error" /></h3>
            </div>
            <div class="modal-body" id="modalErrorBody"><spring:message code="error.chat.unknown" /></div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal"><spring:message code="button.chat.error.dismiss" /></button>
            </div>
        </div>
        <script type="text/javascript">
            var send, disconnect;
            $(document).ready(function() {
                var modalError = $("#modalError");
                var modalErrorBody = $("#modalErrorBody");
                var chatLog = $('#chatLog');
                var messageArea = $('#messageArea');
                var username = '${pageContext.request.userPrincipal.name}';
                var otherJoined = false;

                if(!("WebSocket" in window)) {
                    modalErrorBody.text('<spring:message code="error.chat.websocket.unsupported" javaScriptEscape="true" />');
                    modalError.modal('show');
                    return;
                }

                var infoMessage = function(m) {
                    chatLog.append($('<div>').addClass('informational')
                            .text(moment().format('h:mm:ss a') + ': ' + m));
                };
                infoMessage('<spring:message code="message.chat.connecting" javaScriptEscape="true" />');
                
                var messageContent = function(message) {
               		var result;
                	if (message.userContent && message.userContent != null &&
                            message.userContent.length > 0) {
                		result = message.userContent;
                	} else if (message.localizedContent && message.localizedContent != null &&
                			message.localizedContent.length > 0){
                		result = message.localizedContent;
                	} else {
                		result = message.content;
                	}
                	return result;
                };

                var objectMessage = function(message) {
                    var log = $('<div>');
                    var date = message.timestamp == null ? '' :
                            moment.unix(message.timestamp).format('h:mm:ss a');
                    var msgContent = messageContent(message);
                    if(message.user != null) {
                        var c = message.user == username ? 'user-me' : 'user-you';
                        log.append($('<span>').addClass(c)
                                        .text(date+' '+message.user+':\xA0'))
                                .append($('<span>').text(msgContent));
                    } else {
                        log.addClass(message.type == 'ERROR' ? 'error' :
                                'informational')
                                .text(date + ' ' + msgContent);
                    }
                    chatLog.append(log);
                };

                var server;
                try {
                    server = new WebSocket('ws://' + window.location.host +
                            '<c:url value="/chat/${chatSessionId}" />');
                    server.binaryType = 'arraybuffer';
                } catch(error) {
                    modalErrorBody.text(error);
                    modalError.modal('show');
                    return;
                }

                server.onopen = function(event) {
                    infoMessage('<spring:message code="message.chat.connected" javaScriptEscape="true" />');
                };

                server.onclose = function(event) {
                    if(server != null)
                        infoMessage('<spring:message code="message.chat.disconnected" javaScriptEscape="true" />');
                    server = null;
                    if(!event.wasClean || event.code != 1000) {
                        modalErrorBody.text('<spring:message code="error.chat.code" javaScriptEscape="true" /> ' + event.code + ': ' +
                                event.reason);
                        modalError.modal('show');
                    }
                };

                server.onerror = function(event) {
                    modalErrorBody.text(event.data);
                    modalError.modal('show');
                };

                server.onmessage = function(event) {
                    if(event.data instanceof ArrayBuffer) {
                        var message = JSON.parse(String.fromCharCode.apply(
                                null, new Uint8Array(event.data)
                        ));
                        objectMessage(message);
                        if(message.type == 'JOINED') {
                            otherJoined = true;
                            if(username != message.user)
                                infoMessage('<spring:message code="message.chat.joined" javaScriptEscape="true" />'
                                        .replace('{0}', message.user));
                        }
                    } else {
                        modalErrorBody.text('<spring:message code="error.chat.unexpected.type" javaScriptEscape="true" />'
                                .replace('{0}', typeof(event.data)));
                        modalError.modal('show');
                    }
                };

                send = function() {
                    if(server == null) {
                        modalErrorBody.text('<spring:message code="error.chat.not.connected" javaScriptEscape="true" />');
                        modalError.modal('show');
                    } else if(!otherJoined) {
                        modalErrorBody.text('<spring:message code="error.chat.not.joined" javaScriptEscape="true" />');
                        modalError.modal('show');
                    } else if(messageArea.get(0).value.trim().length > 0) {
                        var message = {
                            timestamp: new Date(), type: 'TEXT', user: username,
                            content: messageArea.get(0).value
                        };
                        try {
                            var json = JSON.stringify(message);
                            var length = json.length;
                            var buffer = new ArrayBuffer(length);
                            var array = new Uint8Array(buffer);
                            for(var i = 0; i < length; i++) {
                                array[i] = json.charCodeAt(i);
                            }
                            server.send(buffer);
                            messageArea.get(0).value = '';
                        } catch(error) {
                            modalErrorBody.text(error);
                            modalError.modal('show');
                        }
                    }
                };

                disconnect = function() {
                    if(server != null) {
                        infoMessage('<spring:message code="message.chat.disconnected" javaScriptEscape="true" />');
                        server.close();
                        server = null;
                    }
                };

                window.onbeforeunload = disconnect;
            });
        </script>
    </jsp:body>
</template:basic>
