import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {

    final static String token = "OTUzNjI4MjQxNTE4ODIxNDI2.YjHVgg.vtooj79fOfffIUE6xSjkPqCkQJY";
    final static DiscordClient client = DiscordClient.create(token); //Creamos un cliente de Discord.
    final static GatewayDiscordClient gateway = client.login().block(); //Creamos el gateaway para que dicho cliente se logee.

    public static void main(final String[] args) {


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage(); //Aquí lee los mensajes escritos en los canales.
            final MessageChannel channel = message.getChannel().block();
            if ("!ping".equals(message.getContent())) { // Método simple que si el mensaje equivale a un !ping, el bot devuelva un !pong en su mismo canal el cual anteriormente ha creado.
                final MessageChannel channel2 = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }
            if(message.getContent().startsWith("!embed")){  //Condicional para que si el mensaje es !embed devuelva el mensaje correspondiente

                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.GREEN)
                        .title("Patata")
                        .image("attachment://patata.jpeg")
                        .build();

                InputStream fileAsInputStream = null;
                try {
                    fileAsInputStream = new FileInputStream("patata.jpeg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                channel.createMessage(MessageCreateSpec.builder()
                        .content("Es una patata")
                        .addFile("patata.jpeg", fileAsInputStream)
                        .addEmbed(embed)
                        .build()).subscribe();
            }

        });


        gateway.onDisconnect().block(); //Cuando se desconecta del gateaway bloquea al final el bot
    }
}