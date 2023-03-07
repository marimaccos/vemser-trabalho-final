package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Venda;
import freemarker.core.ParseException;
import freemarker.template.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "kellyplcastelo@gmail.com"; // botei meu email pessoal aqui,
        // mas acho interessante a gente repensar e quem sabe adicionar um email pros usuarios

    private final JavaMailSender emailSender;

    public void sendEmail(String template) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(TO);
            mimeMessageHelper.setSubject("E-mail Template");
            mimeMessageHelper.setText(template, true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar e-mail.");
        }
    }

    public String getVendaTemplate(Venda venda, Integer acao) throws RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", venda.getComprador().getNome());
        dados.put("codigo", venda.getCodigo());
        dados.put("email", from);
        Template template = null;

        try {

            switch (acao) {
                case 1:
                    template = fmConfiguration.getTemplate("venda-cancelada-template.ftl");
                    break;
                case 2:
                    template = fmConfiguration.getTemplate("venda-realizada-template.ftl");
                    break;
            }
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        } catch (TemplateException | RuntimeException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar e-mail.");
        }
    }
}
