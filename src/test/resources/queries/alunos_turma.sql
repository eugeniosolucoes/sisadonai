/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  eugenio
 * Created: 29/02/2016
 */

SELECT DISTINCT 
    dp.`CPF_PFisica`, 
    pf.`Nome_PFisica`, 
    pf.`Codigo_PFisica`, 
    tu.`Nome_Turma`, 
    m.`Codigo_Situacao_Aluno`,
    mens.`Data_Vencimento`
FROM ((((((((`PFisicas` pf
INNER JOIN `DocumentosPFisicas` dp ON dp.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`)
INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`)
INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`)
INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`)
INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`)
INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo`  
AND mens.`Codigo_Curso` = c.`Codigo_Curso` 
AND mens.`Codigo_Serie` = s.`Codigo_Serie` 
AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` )
WHERE 1 = 1 
AND tu.`Codigo_Turma` = '020'
AND YEAR(mens.`Data_Vencimento`) = 2016 
AND MONTH(mens.`Data_Vencimento`) = 3 
AND m.`Codigo_Situacao_Aluno` = '01'
ORDER BY pf.`Nome_PFisica`;



