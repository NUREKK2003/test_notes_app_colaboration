package com.nomteam.csvnotesapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nomteam.csvnotesapp.ui.theme.TestNotesAppTheme
import com.nomteam.csvnotesapp.R

@Composable
fun AboutScreen(){
    Column {
        Title(title = "About app",Modifier.padding(top = 10.dp, start = 10.dp))
        Divider(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))

        Text(
            text = "",//stringResource(R.string.app_description),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Divider(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
        Autors(Modifier.padding(start = 10.dp))
    }
}

@Composable
fun Title(
    title:String,
    modifier: Modifier=Modifier
){
    Text(
        text = title,
        fontStyle = MaterialTheme.typography.h1.fontStyle,
        modifier = modifier
    )
}

@Composable
fun Autors(
    modifier: Modifier=Modifier
){

    val ppandtosLink = buildAnnotatedString {
        append("Privacy Policy and Terms of Use: ")
        pushStringAnnotation(tag = "ppandtos", annotation = "https://google.com")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("https://google.com")
        }
        pop()
    }
    val nurekGitLink = buildAnnotatedString {
        pushStringAnnotation(tag = "git", annotation = "https://github.com/NUREKK2003")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("https://github.com/NUREKK2003")
        }
        pop()
    }
    val oskarGitLink = buildAnnotatedString {
        pushStringAnnotation(tag = "git", annotation = "https://github.com/jaszczurga")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("https://github.com/jaszczurga")
        }
        pop()
    }
    val montasGitLink = buildAnnotatedString {
        pushStringAnnotation(tag = "git", annotation = "https://github.com/Montaso")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("https://github.com/Montaso")
        }
        pop()
    }
    Column(modifier = modifier) {
        Text(text = "App creators:")
        Spacer(modifier = Modifier.height(10.dp))
        Person(name = "Jakub Nurkiewicz", link = nurekGitLink)
        Person(name = "Oskar Gajewski", link = oskarGitLink)
        Person(name = "Mateusz Przyborski", link = montasGitLink)
        Spacer(modifier.weight(1f))
        PPandToS(link = ppandtosLink)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun PPandToS(
    link:AnnotatedString
){
    val uriHandler = LocalUriHandler.current
    ClickableText(text = link, style = MaterialTheme.typography.body1, onClick = { offset ->
        link.getStringAnnotations(tag = "ppandtos", start = offset, end = offset).firstOrNull()?.let {
            uriHandler.openUri(it.item)
        }
    })
}

@Composable
fun Person(
    name:String,
    link:AnnotatedString
){
    val uriHandler = LocalUriHandler.current

    Row(){
        Text(text = name)
        Spacer(modifier = Modifier.width(10.dp))
        ClickableText(text = link, style = MaterialTheme.typography.body1, onClick = { offset ->
            link.getStringAnnotations(tag = "git", start = offset, end = offset).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        })
    }
}


@Preview(showBackground = true)
@Composable
fun AboutPreview(){
    TestNotesAppTheme {
        AboutScreen()
    }
}