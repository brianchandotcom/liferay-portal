<style>

img.display {
	width: 200px;
	height: 150px;
}

div.frontpage {
background-image: url(${Image6kp8.getData()});
color: #333333;
padding-top: 50px;
padding-bottom: 50px;
}

h2-title {
font-size: xx-large;
font-variant: small-caps;
background: -webkit-linear-gradient(#444, #888);
-webkit-background-clip: text;
-webkit-text-fill-color: transparent;
}

td.image1 {
background-image: url(${image1.getData()});
background-repeat: no-repeat;
background-position: center;
}

td.image2 {
background-image: url(${image2.getData()});
background-repeat: no-repeat;
background-position: center;
}

td.image3 {
background-image: url(${image3.getData()});
background-repeat: no-repeat;
background-position: center;
}

</style>

<div class="frontpage">
<center>
	<h2-title>
		${title.getData()}
	</h2-title>
<p>
</p>

<table margin-top="50px" style="width:90%">
	<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
	</colgroup>

	<tr>
<td class="image1" height="150" width="200">
		</td>
<td class="image2" height="150" width="200">
		</td>
<td class="image3" height="150" width="200">
		</td>
	</tr>
	<tr>
<td style="text-align:center">
			<h3> ${heading1.getData()} </h3>
		</td>
<td style="text-align:center">
			<h3> ${heading2.getData()} </h3>
		</td>
<td style="text-align:center">
			<h3> ${heading3.getData()} </h3>
		</td>
	</tr>
	<tr>
<td style="text-align:center">
			${content1.getData()}
		</td>
<td style="text-align:center">
			${content2.getData()}
		</td>
<td style="text-align:center">
			${content3.getData()}
		</td>
	</tr>
</table>
</center>
</div>