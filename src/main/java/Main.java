import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class Main {

    final static String token = "OTUzNjI4MjQxNTE4ODIxNDI2.YjHVgg.vtooj79fOfffIUE6xSjkPqCkQJY";
    final static DiscordClient client = DiscordClient.create(token); //Creamos un cliente de Discord (para el bot).
    final static GatewayDiscordClient gateway = client.login().block(); //Creamos el gateaway para que dicho cliente se logee.

    public static void main(final String[] args) {


        gateway.on(MessageCreateEvent.class).subscribe(event -> { //Es una expresión lambda, que utiliza un evento de mensaje para posteriormente trabajr con ello.
            final Message message = event.getMessage(); //Aqui lee los mensajes escritos en los canales. Es un programa muy simple dado que no filtra por canal ni por usuario
            final MessageChannel channel = message.getChannel().block();
            if ("!ping".equals(message.getContent())) { // Método simple que si el mensaje equivale a un !ping, el bot devuelva un !pong en su mismo canal el cual anteriormente ha creado.
                final MessageChannel channel2 = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }
        });

        gateway.onDisconnect().block(); //Cuando se desconecta del gateaway bloquea al final el bot
    }
}